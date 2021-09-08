package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSService;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CONTAINER_PLATFORM;

public class KubernetesService extends CaaSService {

    protected KubernetesService() {

    }

    public static KubernetesServiceBuilder builder() {
        return new KubernetesServiceBuilder();
    }

    public static class KubernetesServiceBuilder extends Component.Builder<KubernetesService, KubernetesService.KubernetesServiceBuilder> {

        @Override
        protected KubernetesService createComponent() {
            return new KubernetesService();
        }

        @Override
        protected KubernetesServiceBuilder getBuilder() {
            return this;
        }

        @Override
        public KubernetesService build() {
            component.setType(CONTAINER_PLATFORM);
            return super.build();
        }
    }

}
