# mysqldump --help
# cat dbnames_to_dump.txt  | xargs -I {} echo back_{}_to_{}

curtime=`date +%Y%m%d%H%M%S`
curdate=`date +%Y%m%d`
backpath=back_$curdate

mkdir -p $backpath

cat dbnames_to_dump.txt  | xargs -I {} sh -c "mysqldump -u root -proot123  {} > $backpath/{}.dump.sql"


