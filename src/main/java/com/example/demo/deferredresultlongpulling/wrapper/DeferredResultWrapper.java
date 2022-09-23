package com.example.demo.deferredresultlongpulling.wrapper;

import com.example.demo.deferredresultlongpulling.vo.LoginResultVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;


public class DeferredResultWrapper implements Comparable<DeferredResultWrapper> {
    private static final ResponseEntity<LoginResultVo>
            NOT_MODIFIED_RESPONSE_LIST = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    private Map<String, String> normalizedNamespaceNameToOriginalNamespaceName;
    private DeferredResult<ResponseEntity<LoginResultVo>> result;


    public DeferredResultWrapper(long timeoutInMilli) {
        result = new DeferredResult<>(timeoutInMilli, NOT_MODIFIED_RESPONSE_LIST);
    }


    public void onTimeout(Runnable timeoutCallback) {
        result.onTimeout(timeoutCallback);
    }

    public void onCompletion(Runnable completionCallback) {
        result.onCompletion(completionCallback);
    }

    /**
     * The namespace name is used as a key in client side, so we have to return the original one instead of the correct one
     */
    public void setResult(LoginResultVo loginResultVo) {
        result.setResult(new ResponseEntity<>(loginResultVo, HttpStatus.OK));
    }

    public DeferredResult<ResponseEntity<LoginResultVo>> getResult() {
        return result;
    }

    @Override
    public int compareTo(@NonNull DeferredResultWrapper deferredResultWrapper) {
        return Integer.compare(this.hashCode(), deferredResultWrapper.hashCode());
    }
}
