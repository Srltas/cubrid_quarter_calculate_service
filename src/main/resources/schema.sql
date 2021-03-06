DROP TABLE IF EXISTS excel_data_tb CASCADE;
DROP TABLE IF EXISTS holiday_work_tb CASCADE;
DROP TABLE IF EXISTS quarter_work_calculate_tb CASCADE;
DROP TABLE IF EXISTS users_tb CASCADE;

CREATE TABLE users_tb
(
    seq                  bigint      NOT NULL AUTO_INCREMENT,        -- PK
    name                   varchar(50) NOT NULL,                     -- 이름
    PRIMARY KEY (seq)
);

CREATE TABLE excel_data_tb
(
    seq                 bigint      NOT NULL AUTO_INCREMENT,        -- PK
    days                date        NOT NULL,                       -- 날짜
    day_week            char(1)     NOT NULL,                       -- 요일
    name                varchar     NOT NULL,                       -- 이름
    begin_time          int         NOT NULL,                       -- 출근시간
    end_time            int         NOT NULL,                       -- 퇴근시간
    total_time          int         NOT NULL,                       -- 근로시간
    night_time          int         NOT NULL,                       -- 야간 근로시간
    holiday_time        int         NOT NULL,                       -- 휴일 근로시간
    leave_time          int         NOT NULL,                       -- 휴가시간
    holiday_check       char(1)     NOT NULL,                       -- 휴일체크
    PRIMARY KEY (seq)
);

CREATE TABLE holiday_work_tb
(
    seq                 bigint      NOT NULL,                       -- PK
    user_seq            bigint      NOT NULL,                       -- FK (users_tb pk)
    days                date        NOT NULL,                       -- 날짜
    holiday_holiday     int         NOT NULL,                       -- 휴일휴일 근무시간
    holiday_weekday     int         NOT NULL,                       -- 휴일평일 근무시간
    weekday_holiday     int         NOT NULL,                       -- 평일휴일 근무시간
    holiday_8H_over     int         NOT NULL,                       -- 휴일 8시간 초과 근무시간
    PRIMARY KEY (seq),
    FOREIGN KEY (user_seq) REFERENCES users_tb (seq)
);


CREATE TABLE quarter_work_calculate_tb                               -- 사용자 종합 시간 정보
(
    seq                  bigint      NOT NULL AUTO_INCREMENT,        -- PK
    user_seq             varchar(10) NOT NULL,                       -- FK (user_tb pk)
    quarter              char(1)     NOT NULL,
    quarter_total_time   int         NOT NULL,                       -- 총 근무시간
    quarter_legal_time   int         NOT NULL,                       -- 법정 근로시간
    quarter_work         int         DEFAULT 0,                      -- 분기 근로시간 (내가 한 분기에 일한 시간??)
    prescribed_over_work int         DEFAULT 0,                      -- 소정근로 연장시간 (일한 시간 > 분기 근로 시간 -> 일한 시간 - 법정 근로 시간 - 분기 근로 시간)
    legal_over_work      int         DEFAULT 0,                      -- 법정근로 연장시간 (일한 시간 - 휴가 > 법정 근로 시간 -> 일한 시간 - 휴가 - 법정 근로 시간)
    night_work           int         DEFAULT 0,                      -- 야근 근로시간
    holiday_work         int         DEFAULT 0,                      -- 휴일 근로시간
    holiday_8H_over      int         DEFAULT 0,                      -- 공휴일 8시간 초과
    leave                int         DEFAULT 0,                      -- 사용한 휴가
    compensation_leave   int         DEFAULT 0,                      -- 보상 휴가 시간 = 소정근로 연장시간 * 1
    quarter_money        int         DEFAULT 0,                      -- 법정근로 연장시간(분 제외) * 1.5 + 야간 근로시간(분 포함) * 0.5 + 휴일 근로시간(분 포함) * 0.5 + 공휴일 8시간 초과(분 포함) * 0.5 = 총 계산 값(분 제외)
    quarter_total        int         DEFAULT 0,                      -- compensation_leave + quarter_money
    PRIMARY KEY (seq)
);