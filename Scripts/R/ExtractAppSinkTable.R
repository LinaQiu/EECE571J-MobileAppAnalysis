## Read table in 
app_sink_amandroid_withoutSinkPara <- read.csv("/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/sinks_freq-each-app-Amandroid-withoutSinkPara.csv",header = TRUE)

library(tidyr) 
app_sink_amandroid_withoutSinkPara_short <- spread(app_sink_amandroid_withoutSinkPara,appName, frequency)
write.csv(app_sink_amandroid_withoutSinkPara_short, file = "/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/app_sink_amandroid_withoutSinkPara.csv")


## Read table in 
app_sink_amandroid <- read.csv("/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/sinks_freq-each-app-Amandroid.csv",header = TRUE)

app_sink_amandroid <- spread(app_sink_amandroid,appName, frequency)
write.csv(app_sink_amandroid, file = "/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/app_sink_amandroid.csv")


## Read table in 
app_sink_IccTA <- read.csv("/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/sinks_freq-each-app-IccTA.csv",header = TRUE)

app_sink_IccTA <- spread(app_sink_IccTA,appName, frequency)
write.csv(app_sink_IccTA, file = "/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/app_sink_IccTA.csv")

## Read table in 
app_sink_FlowDroid <- read.csv("/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/sinks_freq-each-app-FlowDroid.csv",header = TRUE)

app_sink_FlowDroid <- spread(app_sink_FlowDroid,appName, frequency)
write.csv(app_sink_FlowDroid, file = "/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/app_sink_FlowDroid.csv")
