package com.shulei.ssm.service;

import com.shulei.ssm.domain.SysLog;

import java.util.List;

public interface ISysLogService {
    public void save(SysLog sysLog) throws Exception;

    List<SysLog> findAll()  throws Exception;
}
