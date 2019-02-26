# 참고 사항

### Docker 사용
docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=sangmessi -e POSTGRES_DB=springdata --name postgres_boot -d postgres

docker exec -i -t postgres_boot bash

su - postgres

psql springdata

데이터베이스 조회
\list

테이블 조회
\dt
