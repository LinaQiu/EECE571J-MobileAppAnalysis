## We will use function symbols to help draw the bubble plot.
## Read data for FlowDroid and DroidSafe
flowDroid_intersect <- read.csv("/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/AnalysisResults/GooglePlayApp/PerformanceResults/Intersect_FD.csv", header = TRUE)
droidSafe_intersect <- read.csv("/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/AnalysisResults/GooglePlayApp/PerformanceResults/Intersect_DS.csv", header = TRUE)
flowDroid <- read.csv("/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/AnalysisResults/GooglePlayApp/PerformanceResults/Processed_GooglePlay_FD_results.csv", header = TRUE)

## Create a new column to store jarSize in MB
flowDroid_intersect$dex2jarSizeInMB <- flowDroid_intersect$dex2JarSize/1000000
droidSafe_intersect$dex2jarSizeInMB <- droidSafe_intersect$dex2JarSize/1000000
flowDroid$dex2jarSizeInMB <- flowDroid$dex2JarSize/1000000

## Create a new column to store analysis time in Minutes
flowDroid_intersect$AnalysisTimeInMin <- flowDroid_intersect$AnalysisTime..s./60
droidSafe_intersect$AnalysisTimeInMin <- droidSafe_intersect$AnalysisTime..s./60
flowDroid$AnalysisTimeInMin <- flowDroid$AnalysisTime..s./60

radius_FD <- sqrt(flowDroid_intersect$numOfFlows/pi)
#symbols(flowDroid_intersect$dex2jarSizeInMB,flowDroid_intersect$AnalysisTimeInMin,circles = radius_FD, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("FlowDroid (FLOWS)"),bg=1:nrow(flowDroid_intersect))

radius_FD_SOURCES <- sqrt(flowDroid_intersect$numOfSources/pi)
#symbols(flowDroid_intersect$dex2jarSizeInMB,flowDroid_intersect$AnalysisTimeInMin,circles = radius_FD_SOURCES, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("FlowDroid (SOURCES)"),bg=1:nrow(flowDroid_intersect))

radius_FD_SINKS <- sqrt(flowDroid_intersect$numOfSinks/pi)
#symbols(flowDroid_intersect$dex2jarSizeInMB,flowDroid_intersect$AnalysisTimeInMin,circles = radius_FD_SINKS, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("FlowDroid (SINKS)"),bg=1:nrow(flowDroid_intersect))

radius_DS <- sqrt(droidSafe_intersect$numOfFlows/pi)
#symbols(droidSafe_intersect$dex2jarSizeInMB,droidSafe_intersect$AnalysisTimeInMin,circles = radius_DS, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("DroidSafe (FLOWS)"),bg=1:nrow(flowDroid_intersect))

radius_DS_SOURCES <- sqrt(droidSafe_intersect$numOfSources/pi)
#symbols(droidSafe_intersect$dex2jarSizeInMB,droidSafe_intersect$AnalysisTimeInMin,circles = radius_DS_SOURCES, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("DroidSafe (SOURCES)"),bg=1:nrow(flowDroid_intersect))

radius_DS_SINKS <- sqrt(droidSafe_intersect$numOfSinks/pi)
#symbols(droidSafe_intersect$dex2jarSizeInMB,droidSafe_intersect$AnalysisTimeInMin,circles = radius_DS_SINKS, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("DroidSafe (SINKS)"),bg=1:nrow(droidSafe_intersect))


#symbols(flowDroid_intersect$dex2jarSizeInMB,flowDroid_intersect$AnalysisTimeInMin,circles = radius_FD, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("FlowDroid (FLOWS)"))
#symbols(flowDroid_intersect$dex2jarSizeInMB,flowDroid_intersect$AnalysisTimeInMin,circles = radius_FD_SOURCES, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("FlowDroid (SOURCES)"))
#symbols(flowDroid_intersect$dex2jarSizeInMB,flowDroid_intersect$AnalysisTimeInMin,circles = radius_FD_SINKS, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("FlowDroid (SINKS)"))
#symbols(droidSafe_intersect$dex2jarSizeInMB,droidSafe_intersect$AnalysisTimeInMin,circles = radius_DS, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("DroidSafe (FLOWS)"))
#symbols(droidSafe_intersect$dex2jarSizeInMB,droidSafe_intersect$AnalysisTimeInMin,circles = radius_DS_SOURCES, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("DroidSafe (SOURCES)"))
#symbols(droidSafe_intersect$dex2jarSizeInMB,droidSafe_intersect$AnalysisTimeInMin,circles = radius_DS_SINKS, inches = 0.5, xlab = "dex2JarSize (MB)", ylab = "Analysis time (minutes)", main = c("DroidSafe (SINKS)"))

## Using ggplot to draw the bubble plots
library(ggplot2)
b1 <- ggplot(flowDroid_intersect, aes(x=dex2jarSizeInMB,y=AnalysisTimeInMin)) +
		geom_point(data = flowDroid_intersect, aes(x=dex2jarSizeInMB, y=AnalysisTimeInMin, size=numOfSources, colour=apkName), alpha=.5)+
		scale_size(range = c(1,15)) +
		theme_bw()+
		theme(legend.position = c(0.65, 0.4), 
				legend.background = element_rect(colour = NA, fill = "white"))+
		scale_color_discrete(guide=FALSE)+
		ggtitle("FlowDroid (sources)")
print(b1)

b2 <- ggplot(flowDroid_intersect, aes(x = dex2jarSizeInMB, y = AnalysisTimeInMin)) +
		geom_point(data=flowDroid_intersect,aes(x=dex2jarSizeInMB, y=AnalysisTimeInMin,size = numOfSinks, colour = apkName), alpha=.5)+
		scale_size(range = c(1,15)) +
		theme_bw()+
		theme(legend.position = c(0.65, 0.4), 
				legend.background = element_rect(colour = NA, fill = "white"))+
		scale_color_discrete(guide=FALSE)+
		ggtitle("FlowDroid (sinks)")
print(b2)


b3 <- ggplot(flowDroid_intersect, aes(x = dex2jarSizeInMB, y = AnalysisTimeInMin)) +
	geom_point(data=flowDroid_intersect,aes(x=dex2jarSizeInMB, y=AnalysisTimeInMin,size = numOfFlows, colour = apkName), alpha=.5)+
	scale_size(range = c(1,15)) +
	theme_bw()+
	theme(legend.position = c(0.65, 0.4), 
				legend.background = element_rect(colour = NA, fill = "white"))+
	scale_color_discrete(guide=FALSE)+
	ggtitle("FlowDroid (flows)")
print(b3)

b4 <- ggplot(droidSafe_intersect, aes(x=dex2jarSizeInMB,y=AnalysisTimeInMin)) +
	geom_point(data = droidSafe_intersect, aes(x=dex2jarSizeInMB, y=AnalysisTimeInMin, size=numOfSources, colour=apkName), alpha=.5)+
	scale_size(range = c(1,15)) +
	theme_bw()+
	theme(legend.position = c(0.8, 0.7), 
				legend.background = element_rect(colour = NA, fill = "white"))+
	scale_color_discrete(guide=FALSE)+
	ggtitle("DroidSafe (sources)")
print(b4)

b5 <- ggplot(droidSafe_intersect, aes(x = dex2jarSizeInMB, y = AnalysisTimeInMin)) +
	geom_point(data=droidSafe_intersect,aes(x=dex2jarSizeInMB, y=AnalysisTimeInMin,size = numOfSinks, colour = apkName), alpha=.5)+
	scale_size(range = c(1,15)) +
	theme_bw()+
	theme(legend.position = c(0.8, 0.7), 
				legend.background = element_rect(colour = NA, fill = "white"))+
	scale_color_discrete(guide=FALSE)+
	ggtitle("DroidSafe (sinks)")
print(b5)


b6 <- ggplot(droidSafe_intersect, aes(x = dex2jarSizeInMB, y = AnalysisTimeInMin)) +
	geom_point(data=droidSafe_intersect,aes(x=dex2jarSizeInMB, y=AnalysisTimeInMin,size = numOfFlows, colour = apkName), alpha=.5)+
	scale_size(range = c(1,15)) +
	theme_bw()+
	theme(legend.position = c(0.8, 0.7), 
				legend.background = element_rect(colour = NA, fill = "white"))+
	scale_color_discrete(guide=FALSE)+
	ggtitle("DroidSafe (flows)")
print(b6)

b7 <- ggplot(flowDroid, aes(x=dex2jarSizeInMB,y=AnalysisTimeInMin)) +
	geom_point(data = flowDroid, aes(x=dex2jarSizeInMB, y=AnalysisTimeInMin, size=numOfSources, colour=apkName), alpha=.5)+
	scale_size(range = c(1,15)) +
	theme_bw()+
	theme(legend.position = c(0.2, 0.7), 
				legend.background = element_rect(colour = NA, fill = "white"))+
	scale_color_discrete(guide=FALSE)+
	ggtitle("FlowDroid (sources) [all apps]")
print(b7)

b8 <- ggplot(flowDroid, aes(x = dex2jarSizeInMB, y = AnalysisTimeInMin)) +
	geom_point(data=flowDroid,aes(x=dex2jarSizeInMB, y=AnalysisTimeInMin,size = numOfSinks, colour = apkName), alpha=.5)+
	scale_size(range = c(1,15)) +
	theme_bw()+
	theme(legend.position = c(0.2, 0.7), 
				legend.background = element_rect(colour = NA, fill = "white"))+
	scale_color_discrete(guide=FALSE)+
	ggtitle("FlowDroid (sinks) [all apps]")
print(b8)


b9 <- ggplot(flowDroid, aes(x = dex2jarSizeInMB, y = AnalysisTimeInMin)) +
	geom_point(data=flowDroid,aes(x=dex2jarSizeInMB, y=AnalysisTimeInMin,size = numOfFlows, colour = apkName), alpha=.5)+
	scale_size(range = c(1,15)) +
	theme_bw()+
	theme(legend.position = c(0.2, 0.7), 
				legend.background = element_rect(colour = NA, fill = "white"))+
	scale_color_discrete(guide=FALSE)+
	ggtitle("FlowDroid (flows) [all apps]")
print(b9)

require(gridExtra)
grid.arrange(b1,b2,b3, ncol=3)
grid.arrange(b4,b5,b6, ncol=3)
grid.arrange(b7,b8,b9, ncol=3)

grid.arrange(b4,b5,b6,b1,b2,b3,b7,b8,b9,ncol=3)
