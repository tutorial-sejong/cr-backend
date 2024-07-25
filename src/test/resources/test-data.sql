-- 강의 일정 데이터 삽입
INSERT INTO course_schedule (sch_dept_alias, curi_no, class_no, sch_college_alias, curi_nm, curi_lang_nm,
                             curi_type_cd_nm, slt_domain_cd_nm, tm_num, student_year, cors_unit_grp_cd_nm,
                             manage_dept_nm, lesn_emp, lesn_time, lesn_room, cyber_type_cd_nm,
                             internship_type_cd_nm, inout_sub_cdt_exchange_yn, remark)
VALUES ('CS', 'CS101', '01', 'ENG', '컴퓨터과학개론', '한국어',
        '전공필수', '전공', '3', '1', '3학점',
        '컴퓨터공학과', '홍길동', '월수금 9:00-10:30', '공학관 101호', '오프라인',
        '해당없음', 'N', '비고 없음');

INSERT INTO course_schedule (sch_dept_alias, curi_no, class_no, sch_college_alias, curi_nm, curi_lang_nm,
                             curi_type_cd_nm, slt_domain_cd_nm, tm_num, student_year, cors_unit_grp_cd_nm,
                             manage_dept_nm, lesn_emp, lesn_time, lesn_room, cyber_type_cd_nm,
                             internship_type_cd_nm, inout_sub_cdt_exchange_yn, remark)
VALUES ('MATH', 'MATH101', '02', 'SCI', '미적분학', '영어',
        '전공선택', '전공', '3', '1', '3학점',
        '수학과', '김철수', '화목 13:00-14:30', '과학관 201호', '오프라인',
        '해당없음', 'N', '비고 없음');
