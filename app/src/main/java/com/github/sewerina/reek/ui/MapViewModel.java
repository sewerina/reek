package com.github.sewerina.reek.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.sewerina.reek.model.ReekMarker;
import com.github.sewerina.reek.service.MosDataService;

import java.util.List;

public class MapViewModel extends ViewModel {
    private final MutableLiveData<List<ReekMarker>> mIndustrialCompaniesMarkers = new MutableLiveData<>();
    private final MutableLiveData<List<ReekMarker>> mLargeWasteBinsMarkers = new MutableLiveData<>();
    private MosDataService mMosDataService;

    public MapViewModel(MosDataService service) {
        mMosDataService = service;
    }

    public LiveData<List<ReekMarker>> getIndustrialCompaniesMarkers() {
        return mIndustrialCompaniesMarkers;
    }

    public LiveData<List<ReekMarker>> getLargeWasteBinsMarkers() {
        return mLargeWasteBinsMarkers;
    }

    public void load() {
        mMosDataService.loadIndustrialCompanies(mIndustrialCompaniesMarkers);
        mMosDataService.loadLargeWasteBins(mLargeWasteBinsMarkers);
    }

    public void cancel() {
        mMosDataService.cancelRequests();
    }
}
