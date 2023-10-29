package com.genie.gymgenie.utils;

import com.genie.gymgenie.config.RateLimitConfig;
import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

import static com.genie.gymgenie.security.CurrentUser.getCurrentUserDetails;
import static com.genie.gymgenie.utils.ExceptionThrower.*;

@Component
@RequiredArgsConstructor
public class RateLimitExecutor {

    private final RateLimitConfig rateLimit;
    public final GenieLogger genie = new GenieLogger(RateLimitExecutor.class);

    public ApiResponse executeAuth(Supplier<ApiResponse> action, String operationDescription) {
        if (operationDescription.equals("Login") || operationDescription.equals("Register")){
            if (rateLimit.twentyBucket().tryConsume(1)){
                genie.info(operationDescription + " request received");
                return action.get();
            } else {
                throw authException(TOO_MANY_REQUESTS.formatted(operationDescription), HttpStatus.TOO_MANY_REQUESTS);
            }
        } else {
            if (rateLimit.fiveBucket().tryConsume(1)){
                genie.info(operationDescription + " request received");
                return action.get();
            } else {
                throw authException(TOO_MANY_REQUESTS.formatted(operationDescription), HttpStatus.TOO_MANY_REQUESTS);
            }
        }
    }

    public ApiResponse executeUser(Supplier<ApiResponse> action, String operationDescription) {
        if (rateLimit.fiveBucket().tryConsume(1)){
            genie.info(operationDescription + " request received");
            return action.get();
        } else {
            throw authException(TOO_MANY_REQUEST_MSG.formatted(getCurrentUserDetails().getUsername()), HttpStatus.TOO_MANY_REQUESTS);
        }
    }

    public <T> T executeGenie(Supplier<T> action, String operationDescription, String message) {
        if (rateLimit.oneBucket().tryConsume(1)){
            genie.info(operationDescription + " request received");
            return action.get();
        } else if (operationDescription.equals("Workout retrieval")){
            if (rateLimit.fiveBucket().tryConsume(1)){
                genie.info(operationDescription + " request received");
                return action.get();
            } else {
                throw authException(TOO_MANY_REQUESTS.formatted(message), HttpStatus.TOO_MANY_REQUESTS);
            }
        } else {
            throw authException(TOO_MANY_REQUESTS.formatted(message), HttpStatus.TOO_MANY_REQUESTS);
        }
    }
}
