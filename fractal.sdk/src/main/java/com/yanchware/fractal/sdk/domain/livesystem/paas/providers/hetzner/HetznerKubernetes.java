package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.hetzner;

import com.yanchware.fractal.sdk.domain.livesystem.paas.KubernetesCluster;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidAlphanumericsUnderscoresHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

@Getter
@Setter
public class HetznerKubernetes extends KubernetesCluster implements HetznerResourceEntity {
    private final static String REGION_IS_NULL = "[HetznerKubernetes Validation] Region is not specified and it is required";
    private final static String NAME_NOT_VALID = "[KubernetesCluster Validation] The name can contain only letters, numbers, underscores, and hyphens. The name must start and end with a letter or number and must be between 1 and 63 characters long";

    private HetznerRegion hetznerRegion;
    private Map<String, String> tags;
    private String name;

    public ProviderType getProvider() {
        return ProviderType.HETZNER;
    }

    public static HetznerKubernetesBuilder builder() {
        return new HetznerKubernetesBuilder();
    }

    public static class HetznerKubernetesBuilder extends Builder<HetznerKubernetes, HetznerKubernetesBuilder> {

        @Override
        protected HetznerKubernetes createComponent() {
            return new HetznerKubernetes();
        }

        @Override
        protected HetznerKubernetesBuilder getBuilder() {
            return this;
        }

        public HetznerKubernetesBuilder withRegion(HetznerRegion region) {
            component.setHetznerRegion(region);
            return builder;
        }

        public HetznerKubernetesBuilder withName(String name) {
            component.setName(name);
            return builder;
        }

        public HetznerKubernetesBuilder withTags(Map<String, String> tags) {
            component.setTags(tags);
            return builder;
        }

        public HetznerKubernetesBuilder withTag(String key, String value) {
            if (component.getTags() == null) {
                withTags(new HashMap<>());
            }

            component.getTags().put(key, value);
            return builder;
        }
    }

    @Override
    public Collection<String> validate() {

        Collection<String> errors = super.validate();
        errors.addAll(HetznerResourceEntity.validateHetznerResourceEntity(this, "Kubernetes Service"));

        if(StringUtils.isNotBlank(name)) {
            var isAlphaNumerics = isValidAlphanumericsUnderscoresHyphens(name);
            var hasValidLengths = isValidStringLength(name, 1, 63);
            if(!isAlphaNumerics || !hasValidLengths) {
                errors.add(NAME_NOT_VALID);
            }
        }

        if (hetznerRegion == null) {
            errors.add(REGION_IS_NULL);
        }

        return errors;
    }
}
