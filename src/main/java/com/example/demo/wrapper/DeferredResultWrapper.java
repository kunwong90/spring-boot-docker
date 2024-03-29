package com.example.demo.wrapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.Map;


public class DeferredResultWrapper implements Comparable<DeferredResultWrapper> {
    private static final ResponseEntity<List<String>>
            NOT_MODIFIED_RESPONSE_LIST = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    private Map<String, String> normalizedNamespaceNameToOriginalNamespaceName;
    private DeferredResult<ResponseEntity<List<String>>> result;


    public DeferredResultWrapper(long timeoutInMilli) {
        result = new DeferredResult<>(timeoutInMilli, NOT_MODIFIED_RESPONSE_LIST);
    }

    public void recordNamespaceNameNormalizedResult(String originalNamespaceName, String normalizedNamespaceName) {
        if (normalizedNamespaceNameToOriginalNamespaceName == null) {
            normalizedNamespaceNameToOriginalNamespaceName = Maps.newHashMap();
        }
        normalizedNamespaceNameToOriginalNamespaceName.put(normalizedNamespaceName, originalNamespaceName);
    }


    public void onTimeout(Runnable timeoutCallback) {
        result.onTimeout(timeoutCallback);
    }

    public void onCompletion(Runnable completionCallback) {
        result.onCompletion(completionCallback);
    }


    public void setResult(String notification) {
        setResult(Lists.newArrayList(notification));
    }

    /**
     * The namespace name is used as a key in client side, so we have to return the original one instead of the correct one
     */
    public void setResult(List<String> notifications) {
        result.setResult(new ResponseEntity<>(notifications, HttpStatus.OK));
    }

    public DeferredResult<ResponseEntity<List<String>>> getResult() {
        return result;
    }

    @Override
    public int compareTo(@NonNull DeferredResultWrapper deferredResultWrapper) {
        return Integer.compare(this.hashCode(), deferredResultWrapper.hashCode());
    }
}
