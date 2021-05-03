package com.rentalHouseClient.rhc.modules.sys.controller;

import com.rentalHouseClient.rhc.common.controller.BaseController;
import com.rentalHouseClient.rhc.common.dto.R;
import com.rentalHouseClient.rhc.common.util.LocalUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @author czf
 * @date 2021/2/14 17:19
 */
@RestController
@RequestMapping("sys")
public class LocalController extends BaseController {
    @GetMapping(value = "city")
    public R getProvince(String city) {

        LocalUtil lu =  LocalUtil.getInstance();
        List<String> list = lu.getCities("中国", city);



        return  R.ok(list);
    }
    @GetMapping(value = "province")
    public R getProvince() {

        LocalUtil lu =  LocalUtil.getInstance();
        List<String> list = lu.getProvinces("中国");



        return R.ok(list);
    }
    @GetMapping(value = "counties")
    public R getCounties(String city,String counties ) {

        LocalUtil lu =  LocalUtil.getInstance();
        List<String> list = lu.getCounties("中国",city,counties);



            return R.ok(list);
    }


}
