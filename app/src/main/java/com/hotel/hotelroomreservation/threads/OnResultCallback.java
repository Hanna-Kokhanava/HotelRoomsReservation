package com.hotel.hotelroomreservation.threads;

public interface OnResultCallback <Progress, Result> extends OnProgressCallback<Progress> {
    void onSuccess(Result result);
    void onError(Exception e);
}
