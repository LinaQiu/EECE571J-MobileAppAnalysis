## We will use function symbols to help draw the bubble plot.
## Read data for FlowDroid and DroidSafe
flowDroid <- read.csv("/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/AnalysisResults/GooglePlayApp/PerformanceResults/Processed_GooglePlay_FD_results.csv", header = TRUE)
droidSafe <- read.csv("/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/AnalysisResults/GooglePlayApp/PerformanceResults/Processed_GooglePlay_DS_results.csv", header = TRUE)

## Create a new column to store jarSize in MB
flowDroid$jarSizeInMB <- flowDroid$jarSize/1000000
droidSafe$jarSizeInMB <- droidSafe$jarSize/1000000

## Create a new column to store analysis time in Minutes
flowDroid$AnalysisTimeInMin <- flowDroid$AnalysisTime..s./60
droidSafe$AnalysisTimeInMin <- droidSafe$AnalysisTime..s./60

## Get text String that shows how many sources and sinks for each app the tool has found, by combining the # of sources/sinks in the table together
sources_sinks_FD <- paste(flowDroid$X..of.sources,"/",flowDroid$X..of.sinks)
sources_sinks_DS <- paste(droidSafe$X..of.sources,"/",droidSafe$X..of.sinks)

## Draw the bubble plot with data flowDroid and droidSafe
## By default, symbols use the radius to represent the value of the third dimension variable. Because we want to use 
## the circle's size (square) to show the third dimension variable, we calculate the radius with the circle size first.
## S = pi*radius^2; change the baseline size of the circle with parameter "inches"
radius_FD <- sqrt(flowDroid$X..of.flows/pi)
symbols(flowDroid$jarSizeInMB,flowDroid$AnalysisTimeInMin,circles = radius_FD, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("FlowDroid"))
text(flowDroid$jarSizeInMB, flowDroid$AnalysisTimeInMin, sources_sinks_FD)

radius_DS <- sqrt(droidSafe$X..of.flows/pi)
symbols(droidSafe$jarSizeInMB,droidSafe$AnalysisTimeInMin,circles = radius_DS, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("DroidSafe"))
text(droidSafe$jarSizeInMB, droidSafe$AnalysisTimeInMin, sources_sinks_DS)

