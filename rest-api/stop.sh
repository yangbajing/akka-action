#!/usr/bin/env bash
kill $(cat app.pid)
rm app.pid