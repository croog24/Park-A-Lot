package com.googleapis.maps.place;

import java.util.List;

import com.googleapis.maps.place.model.Result;

/**
 * The returned model object from Google Places API.
 * 
 * @author Craig
 *
 */
public class GooglePlacesResponse {

    private List<Object> htmlAttributions = null;
    private List<Result> results = null;
    private String status;
    // Wrapper errorMsg string
    private String errorMsg;

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
