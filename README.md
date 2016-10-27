# chillr
Matrics to track
system.disk.free
system.mem.free
nginx.net.avg_response
mongodb.metrics.document.returnedps
on servers
chillr-netmagic-cloud-bangalore-api
chillr-netmagic-cloud-bangalore-api-2
chillr-netmagic-cloud-bangalore-api-3
chillr-netmagic-cloud-bangalore-api-factory
Represent servers by Square boxes, show average of each matrics on each boxes.
Find the last 1 or 5 minute average of each of these matrics.
Plot the matrics using graph on tapping a particular box.
API to use https://app.datadoghq.com/api/v1/query
Docs: http://docs.datadoghq.com/api/?lang=console#metrics-query
api_key=93af0da2383e1af60fe1bfe42b8ba949
app_key=62dc6e0c5c605f8273c9479b604c0b52e873ef70
show color green, yellow, red on the corresponging average value
system.disk.free: green < 2GB < yellow < 6GB red
system.mem.free: green < 6GB < yellow < 7GB red
nginx.net.avg_response: green < 1 yellow < 5 red
mongodb.metrics.document.returnedps: green < 500 < yellow < 1000 red
