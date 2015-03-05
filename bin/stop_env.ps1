boot2docker ssh "docker ps -a | awk 'NR > 1' | cut -f $# -d' ' | xargs --no-run-if-empty docker kill"
boot2docker ssh "docker ps -a | awk 'NR > 1' | cut -f $# -d' ' | xargs --no-run-if-empty docker rm"
Get-Job mysql | Stop-Job
Get-Job app | Stop-Job
Get-Job selenium | Stop-Job
