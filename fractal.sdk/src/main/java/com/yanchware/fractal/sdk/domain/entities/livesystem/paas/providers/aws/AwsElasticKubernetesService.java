package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.aws;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.KubernetesCluster;
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
public class AwsElasticKubernetesService extends KubernetesCluster implements AwsResourceEntity {
    private final static String REGION_IS_NULL = "[AwsElasticKuernetesService Validation] Region is not specified and it is required";
    private final static String NAME_NOT_VALID = "[KubernetesCluster Validation] The name can contain only letters, numbers, underscores, and hyphens. The name must start and end with a letter or number and must be between 1 and 63 characters long";

    private AwsRegion awsRegion;
    private Map<String, String> tags;
    private String name;

    @Override
    public ProviderType getProvider() {
        return ProviderType.AWS;
    }

    public static AwsElasticKubernetesServiceBuilder builder() {
        return new AwsElasticKubernetesServiceBuilder();
    }

    public static class AwsElasticKubernetesServiceBuilder extends Builder<AwsElasticKubernetesService, AwsElasticKubernetesServiceBuilder> {
        @Override
        protected AwsElasticKubernetesService createComponent() {
            return new AwsElasticKubernetesService();
        }

        @Override
        protected AwsElasticKubernetesServiceBuilder getBuilder() {
            return this;
        }

        public AwsElasticKubernetesServiceBuilder withRegion(AwsRegion region) {
            component.setAwsRegion(region);
            return builder;
        }

        public AwsElasticKubernetesServiceBuilder withName(String name) {
            component.setName(name);
            return builder;
        }

        public AwsElasticKubernetesServiceBuilder withTags(Map<String, String> tags) {
            component.setTags(tags);
            return builder;
        }

        public AwsElasticKubernetesServiceBuilder withTag(String key, String value) {
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
        errors.addAll(AwsResourceEntity.validateAwsResourceEntity(this, "Kubernetes Service"));

        if(StringUtils.isNotBlank(name)) {
            var isAlphaNumerics = isValidAlphanumericsUnderscoresHyphens(name);
            var hasValidLengths = isValidStringLength(name, 1, 63);
            if(!isAlphaNumerics || !hasValidLengths) {
                errors.add(NAME_NOT_VALID);
            }
        }

        if (awsRegion == null) {
            errors.add(REGION_IS_NULL);
        }

        return errors;
    }
}
