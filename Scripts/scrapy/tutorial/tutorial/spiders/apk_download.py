import scrapy 
# from scrapy.contrib.pipeline.files import FilesPipeline, FSFilesStore
import scrapy.pipelines
import scrapy.pipelines.files
import os

FDroid_DOWNLOAD_PATH="/Volumes/LinaQiuHD/Master/EECE571J/Apk/FDroid-new"
ROOT_URL="https://f-droid.org/repository/browse/?page_id=0&fdcategory="

class APKDownloader(scrapy.Spider):
	"""docstring for APKDownloader"""
	name = 'apkdownloader'
	allowed_domains = ['f-droid.org']

	start_urls = ['https://f-droid.org/repository/browse/',]

	def parse(self, response):
		counter=0
		for option in response.xpath('//option/text()').extract():
			if "All categories" not in option:
				counter+=1

				# Because it is kind of tricky to have space in a file path, which needs extra time to take care of. 
				# Therefore, we use the following code to get rid of all spaces in file paths.
				subdirectory=(FDroid_DOWNLOAD_PATH+"/"+option).replace(" ","")
				if not os.path.exists(subdirectory):
					os.makedirs(subdirectory)

				# We have two types of category urls: "https://f-droid.org/repository/browse/?page_id=0&fdcategory=Development" 
				# and "https://f-droid.org/repository/browse/?page_id=0&fdcategory=Science+%26+Education".
				if " & " in option:
					subcategory_url=ROOT_URL+option.replace(" & ","+%26+")
					print subcategory_url
				else:
					subcategory_url=ROOT_URL+option
					print subcategory_url
				yield scrapy.Request(subcategory_url, callback=self.parse_subcategory)
		print "total number of categories: "+str(counter)

	def parse_subcategory(self, response):
		for url in response.xpath('//a/@href').extract():
			#print "url: "+url
			if "&fdid=" in url and "&fdpage=" not in url:
				apk_url=(response.url+"&fdid=").replace("page_id=0&","")
			elif "&fdid=" in url and "&fdpage=" in url:
				apk_url=(response.url)[:(response.url).index("&fdpage=")]+"&fdid="
			elif "&fdid=" not in url and "&fdpage=" in url:
				#print "&fdpage: "+url
				yield scrapy.Request(url, callback=self.parse_subcategory)
				continue
			else:
				continue
			if apk_url in url:
				yield scrapy.Request(url, callback=self.parse_download_apk)
			
	def parse_download_apk(self, response):
		print "Fetch url from sublink..."
		print response.url
		# Retrieve the subcategory name from the response.url, and then parse it as a parameter to save_apk callback.
		subcategory=((response.url).split("https://f-droid.org/repository/browse/?fdcategory=")[-1]).split("&fdid=")[0]
		# See comments in line 21&22 and 27&28
		if "+%26+" in subcategory:
			subcategory=subcategory.replace("+%26+","&")

		apk_urls=[]
		for tag_a in response.xpath('//a').extract():
			if "download apk" in tag_a:
				url_start_index=tag_a.index("\"")+1
				url_end_index=tag_a.index("\"",url_start_index)
				url=tag_a[url_start_index:url_end_index]	
				print "apk url: "+url
				apk_urls.append(url)	
		# According to FDroid website, urls to download a specific apk is ordered by time, which follows the rule that the very first url links to the latest apk.
		# Because DroidSafe is not updated, there is sdk version limitations. Therefore, we will download the apk from the last url, which is the earliest apk.
		# print "download apk url: "+apk_urls[len(apk_urls)-1]
		# yield scrapy.Request(apk_urls[len(apk_urls)-1], meta={'subcategory':subcategory}, callback=self.save_apk)
		
		# We decided to download the latest apk files now. 
		print "download apk url: "+apk_urls[0]
		yield scrapy.Request(apk_urls[0], meta={'subcategory':subcategory}, callback=self.save_apk)

	def save_apk(self, response):
		subcategory=response.meta['subcategory']
		print "The parameter passed is: "+subcategory
		apkName=(response.url).split("/")[-1]
		path=FDroid_DOWNLOAD_PATH+"/"+subcategory+"/"+apkName
		print "path value: "+path
		with open(path,'wb') as outfile:
			outfile.write(response.body)