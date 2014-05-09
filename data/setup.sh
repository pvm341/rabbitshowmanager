#!/bin/bash
psql $USER -h localhost -c '\i database-init.sql' || createdb -T template1 -U $USER -W --lc-collate='en_GB.UTF-8' --lc-ctype='en_GB.UTF-8' -E UTF8 $USER "Personal Database"

