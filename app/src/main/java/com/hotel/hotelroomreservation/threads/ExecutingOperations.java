package com.hotel.hotelroomreservation.threads;

public interface ExecutingOperations<Params, Progress, Result> {
    Result executing(Params params, OnProgressCallback<Progress> progressCallback);
}
