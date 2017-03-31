#!/usr/bin/env bash
curl -v -XPUT -H 'content-type: application/json' \
    -d '{"id":"bb","isbn":"aa-bb","title":"《Akka实战》","author":"羊八井","date":"2015-11-26","description":"Akka实例教程"}' \
    http://localhost:3333/api/book/bb
echo
