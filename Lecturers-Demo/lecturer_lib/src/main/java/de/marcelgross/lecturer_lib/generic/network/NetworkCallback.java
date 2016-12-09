package de.marcelgross.lecturer_lib.generic.network;

public interface NetworkCallback {
    void onFailure();
    void onSuccess(NetworkResponse response);
}
