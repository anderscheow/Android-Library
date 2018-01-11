package io.github.anderscheow.library.services;

public interface ApplicationServiceScheduler {

    void scheduleForService(ApplicationServiceReadyCallback callback);

}
