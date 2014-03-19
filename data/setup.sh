#!/bin/bash
createdb -T template1 -U $USER -W --lc-collate='en_GB.UTF-8' --lc-ctype='en_GB.UTF-8' -E UTF8 rsmtest "Rabbit Show Manager database"
psql rsmtest -U $USER -W -c "\i create-tables.sql"
psql rsmtest -U $USER -W -c "\i inserts-init-data.sql"

      
