#!/bin/bash
psql $USER -U $USER -W -c "\i database-init.sql"

      
