package ru.vseopecheni.app.data;

import javax.inject.Inject;

import ru.vseopecheni.app.data.network.ServiceNetwork;


public class RepositoryManagerImpl implements RepositoryManager {

    private ServiceNetwork serviceNetwork;

    @Inject
    RepositoryManagerImpl(ServiceNetwork serviceNetwork) {
        this.serviceNetwork = serviceNetwork;
    }

    @Override
    public ServiceNetwork getServiceNetwork() {
        return serviceNetwork;
    }
}
