package ru.vseopecheni.app.data.network;

import javax.inject.Inject;


public class ServiceNetworkImp implements ServiceNetwork {

    private static final String TAG = ServiceNetworkImp.class.getSimpleName();

    private ApiMethods apiMethods;

    @Inject
    public ServiceNetworkImp(ApiMethods apiMethods) {
        this.apiMethods = apiMethods;
    }


}
