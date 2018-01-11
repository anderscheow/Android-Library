package io.github.anderscheow.library.services;

public interface ApplicationServiceReadyCallback<T extends ApplicationService> {

    void onServiceReady(T service);

}
