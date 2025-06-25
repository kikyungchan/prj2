USE prj2;

#게시판 테이블
CREATE TABLE board
(
    id         INT AUTO_INCREMENT NOT NULL,
    title      VARCHAR(80)        NOT NULL,
    content    VARCHAR(500)       NULL,
    writer     VARCHAR(20)        NOT NULL,
    created_at datetime           NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_board PRIMARY KEY (id)
);

DROP TABLE board;
