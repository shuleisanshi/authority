package com.shulei.ssm.controller;

import com.shulei.ssm.domain.SysLog;
import com.shulei.ssm.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {
    @Autowired
    ISysLogService sysLogService;
    @Autowired
    private HttpServletRequest request;


    private Date vistitTime;  //开始时间
    private Class clazz;      //访问的类
    private Method method;    //访问的方法

    //前置通知,主要获取开始时间,执行的类是哪一个,执行的是哪一个方法
    @Before("execution(* com.shulei.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws Exception{
        vistitTime = new Date();                            //当前访问的时间
        clazz = jp.getTarget().getClass();                  //获取访问的类
        String methodName = jp.getSignature().getName();    //获取方法的名称

        Object[] args = jp.getArgs();//获取访问的方法的参数

        //获取具体执行的方法的method对象
        if (args==null||args.length==0){
            method = clazz.getMethod(methodName);
        }else {
            Class[] classArgs = new Class[args.length];
            for (int i=0;i<args.length;i++){
                classArgs[i] = args[i].getClass();
            }
            clazz.getMethod(methodName,classArgs);
        }

    }

    //后置通知
    @After("execution(* com.shulei.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {

        long time = System.currentTimeMillis() - vistitTime.getTime();

        //获取url
        String url = "";
        if (clazz!=null&&method!=null&&clazz!=LogAop.class){
            //1.获取类上的@RequestMapping("/orders")
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation!=null){
                String[] clasValue = classAnnotation.value();
                //2. 获取方法上的@RequestMapping(xxx)
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation!=null){
                    String[] methodValue = methodAnnotation.value();
                    url = clasValue[0] + methodValue[0];


                    //  获取访问的IP地址
                    String ip = request.getRemoteAddr();

                    //获取当前操作用户
                    SecurityContext context = SecurityContextHolder.getContext();   //从context获取当前登录的用户
                    User user = (User)context.getAuthentication().getPrincipal();   // 此User是security自定义的User
                    String username = user.getUsername();

                    //将日志相关信息封装到sysLog 中
                    SysLog sysLog = new SysLog();
                    sysLog.setExecutionTime(time);
                    sysLog.setIp(ip);
                    sysLog.setMethod("[类名] "+clazz.getName()+"[方法名] "+method.getName());
                    sysLog.setUrl(url);
                    sysLog.setUsername(username);
                    sysLog.setVisitTime(vistitTime);

                    //调用service完成操作
                    sysLogService.save(sysLog);


                }
            }
        }



    }
}
