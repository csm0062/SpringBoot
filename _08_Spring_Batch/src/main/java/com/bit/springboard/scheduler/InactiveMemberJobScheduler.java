package com.bit.springboard.scheduler;

import com.bit.springboard.config.MemberJobConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class InactiveMemberJobScheduler {
    // 지정한 시간마다 Job을 실행해줄 JobLauncher 객체
    private final JobLauncher jobLauncher;

    private final MemberJobConfiguration memberJobConfiguration;
    private final View error;

    // @Scheduled: 어플리케이션이 실행되는 동안 해당 어노테이션에 지정된 시간마다 지정된 메소드를 실행
    // (cron = "초 분 시 일 월 년")
    @Scheduled(cron ="0 0 0 * * *")
    public void runInactiveMemberJob() {
        //JobInstance를 구별하기 위한 JobParameter 생성
        Map<String, JobParameter<?>> jobParameterMap = new HashMap<>();

        jobParameterMap.put("time", new JobParameter<>(System.currentTimeMillis(), Long.class));

        JobParameters jobParameters = new JobParameters(jobParameterMap);
        
        try {
            log.info("InactiveMemberJob 실행");
            // 1분마다 계속 실행
            jobLauncher.run(memberJobConfiguration.memberJob(), jobParameters);
        } catch (Exception e) {
            log.error("에러 발생: {}", e.getMessage());
        }
    }

}
