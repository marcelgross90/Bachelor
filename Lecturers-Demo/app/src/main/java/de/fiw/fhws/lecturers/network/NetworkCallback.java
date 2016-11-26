package de.fiw.fhws.lecturers.network;

public interface NetworkCallback {
    void onFailure();
    void onSuccess(NetworkResponse response);
}
