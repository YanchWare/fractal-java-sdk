package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci;

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
public class OciContainerEngineForKubernetes extends KubernetesCluster implements OciResourceEntity {
    private final static String REGION_IS_NULL = "[OciContainerEngineForKubernetes Validation] Region is not specified and it is required";
    private final static String NAME_NOT_VALID = "[KubernetesCluster Validation] The name can contain only letters, numbers, underscores, and hyphens. The name must start and end with a letter or number and must be between 1 and 63 characters long";

    private OciRegion ociRegion;
    private Compartment compartment;
    private Map<String, String> tags;
    private String name;

    public ProviderType getProvider() {
        return ProviderType.OCI;
    }

    public static OciContainerEngineForKubernetesBuilder builder() {
        return new OciContainerEngineForKubernetesBuilder();
    }

    public static class OciContainerEngineForKubernetesBuilder extends Builder<OciContainerEngineForKubernetes, OciContainerEngineForKubernetesBuilder> {

        @Override
        protected OciContainerEngineForKubernetes createComponent() {
            return new OciContainerEngineForKubernetes();
        }

        @Override
        protected OciContainerEngineForKubernetesBuilder getBuilder() {
            return this;
        }

        public OciContainerEngineForKubernetesBuilder withRegion(OciRegion region) {
            component.setOciRegion(region);
            return builder;
        }

        public OciContainerEngineForKubernetesBuilder withCompartment(Compartment compartment) {
            component.setCompartment(compartment);
            return builder;
        }


        public OciContainerEngineForKubernetesBuilder withName(String name) {
            component.setName(name);
            return builder;
        }

        public OciContainerEngineForKubernetesBuilder withTags(Map<String, String> tags) {
            component.setTags(tags);
            return builder;
        }

        public OciContainerEngineForKubernetesBuilder withTag(String key, String value) {
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
        errors.addAll(OciResourceEntity.validateOciResourceEntity(this, "Kubernetes Service"));

        if(StringUtils.isNotBlank(name)) {
            var isAlphaNumerics = isValidAlphanumericsUnderscoresHyphens(name);
            var hasValidLengths = isValidStringLength(name, 1, 63);
            if(!isAlphaNumerics || !hasValidLengths) {
                errors.add(NAME_NOT_VALID);
            }
        }

        if (ociRegion == null) {
            errors.add(REGION_IS_NULL);
        }

        return errors;
    }
}
