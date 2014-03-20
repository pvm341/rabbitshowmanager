#!/bin/bash
psql $USER -U $USER -c "\i database-init.sql"

