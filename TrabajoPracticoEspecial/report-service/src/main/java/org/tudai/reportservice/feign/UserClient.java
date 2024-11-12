package org.tudai.reportservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserClient {

    @PatchMapping("/accounts/{accountId}/status")
    void updateAccountStatus(@PathVariable("accountId") Long accountId, @RequestParam("status") boolean status);
}
