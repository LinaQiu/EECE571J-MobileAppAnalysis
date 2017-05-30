## We will use function symbols to help draw the bubble plot.
## Read data for FlowDroid and DroidSafe
flowDroid <- read.csv("/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/AnalysisResults/GooglePlayApp/PerformanceResults/Processed_GooglePlay_FD_results.csv", header = TRUE)
droidSafe <- read.csv("/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/AnalysisResults/GooglePlayApp/PerformanceResults/Processed_GooglePlay_DS_results.csv", header = TRUE)

## Create a new column to store jarSize in MB
flowDroid$dex2jarSizeInMB <- flowDroid$dex2JarSize/1000000
droidSafe$jarSizeInMB <- droidSafe$dex2JarSize/1000000

## Create a new column to store analysis time in Minutes
flowDroid$AnalysisTimeInMin <- flowDroid$AnalysisTime..s./60
droidSafe$AnalysisTimeInMin <- droidSafe$AnalysisTime..s./60

## Get text String that shows how many sources and sinks for each app the tool has found, by combining the # of sources/sinks in the table together
sources_sinks_FD <- paste(flowDroid$numOfSources,"/",flowDroid$numOfSinks)
sources_sinks_DS <- paste(droidSafe$X..of.sources,"/",droidSafe$X..of.sinks)

## Draw the bubble plot with data flowDroid and droidSafe
## By default, symbols use the radius to represent the value of the third dimension variable. Because we want to use 
## the circle's size (square) to show the third dimension variable, we calculate the radius with the circle size first.
## S = pi*radius^2; change the baseline size of the circle with parameter "inches"
radius_FD <- sqrt(flowDroid$numOfFlows/pi)
#symbols(flowDroid$jarSizeInMB,flowDroid$AnalysisTimeInMin,circles = radius_FD, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("FlowDroid"), fg="red", bg="red")
#text(flowDroid$jarSizeInMB, flowDroid$AnalysisTimeInMin, sources_sinks_FD)
symbols(flowDroid$dex2JarSize,flowDroid$AnalysisTimeInMin,circles = radius_FD, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("FlowDroid"))

radius_FD_1 <- sqrt(flowDroid$dex2jarSizeInMB/pi)
symbols(flowDroid$numOfFlows,flowDroid$AnalysisTimeInMin,circles = radius_FD_1, inches = 0.5, xlab = "# of flows", ylab = "Analysis time (minutes)", main = c("FlowDroid"))

radius_DS <- sqrt(droidSafe$X..of.flows/pi)
#symbols(droidSafe$jarSizeInMB,droidSafe$AnalysisTimeInMin,circles = radius_DS, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("DroidSafe"), fg="red", bg="red")
#text(droidSafe$jarSizeInMB, droidSafe$AnalysisTimeInMin, sources_sinks_DS)
symbols(droidSafe$jarSizeInMB,droidSafe$AnalysisTimeInMin,circles = radius_DS, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("DroidSafe"))
radius_DS_1 <- sqrt(droidSafe$jarSizeInMB/pi)
symbols(droidSafe$X..of.flows,droidSafe$AnalysisTimeInMin,circles = radius_DS_1, inches = 0.5, xlab = "# of flows", ylab = "Analysis time (minutes)", main = c("DroidSafe"))

radius_FD <- sqrt(flowDroid$numOfFlows/pi)
symbols(flowDroid$dex2jarSizeInMB,flowDroid$AnalysisTimeInMin,circles = radius_FD, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("FlowDroid (FLOWS)"))

radius_FD_SOURCES <- sqrt(flowDroid$numOfSources/pi)
symbols(flowDroid$dex2jarSizeInMB,flowDroid$AnalysisTimeInMin,circles = radius_FD_SOURCES, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("FlowDroid (SOURCES)"))

radius_FD_SINKS <- sqrt(flowDroid$numOfSinks/pi)
symbols(flowDroid$dex2jarSizeInMB,flowDroid$AnalysisTimeInMin,circles = radius_FD_SINKS, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("FlowDroid (SINKS)"))

radius_DS <- sqrt(droidSafe$X..of.flows/pi)
symbols(droidSafe$jarSizeInMB,droidSafe$AnalysisTimeInMin,circles = radius_DS, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("DroidSafe (FLOWS)"))

radius_DS_SOURCES <- sqrt(droidSafe$X..of.sources/pi)
symbols(droidSafe$jarSizeInMB,droidSafe$AnalysisTimeInMin,circles = radius_DS_SOURCES, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("DroidSafe (SOURCES)"))

radius_DS_SINKS <- sqrt(droidSafe$X..of.sinks/pi)
symbols(droidSafe$jarSizeInMB,droidSafe$AnalysisTimeInMin,circles = radius_DS_SINKS, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("DroidSafe (SINKS)"))

## Using ggplot to draw the bubble plots
library(ggplot2)
