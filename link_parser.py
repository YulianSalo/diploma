from bs4 import BeautifulSoup
import urllib.request
import re
import sys
from os import path

# HTML page parser function. Returns parsed HTML page
def html_page_parse(given_page):

    html_page = urllib.request.urlopen(given_page)
    soup = BeautifulSoup(html_page, "html.parser")
    
    return soup

# HTML tag searcher. Returns the last string found by the specified pattern
def search_in_tag(html_tag_1, html_tag_2, reg_ex_pattern, soup):

    all_href = []

    for td in soup.find_all( lambda tag: tag.name==html_tag_1 and re.findall(reg_ex_pattern, tag.text.strip() ) ) :
        link = td.find_next(html_tag_2)
        all_href.append(link["href"] if link else "")

    return all_href[-1]

def main():
    
    error_message = "Please, specify one of the Raspberry Pi OS system arguments: 'rpi_os_lite', 'rpi_os_desk', 'rpi_os_desk_plus'."
    
    try:
        # given website
        given_link = ""
        release_index_file = ""

        # checking if system argument was specified as the one for the RPI OS Lite
        if sys.argv[1] == "rpi_os_lite" and len(sys.argv) == 2: 
            
            # forming an appropriate start URL and an appropriate release index file
            given_link = "https://downloads.raspberrypi.org/raspios_lite_armhf/images/"
            release_index_file = "rpi_os_lite_rel.txt"

        # checking if system argument was specified as the one for the RPI OS Desktop
        elif sys.argv[1] == "rpi_os_desk" and len(sys.argv) == 2: 
            
            # forming an appropriate start URL and an appropriate release index file
            given_link = "https://downloads.raspberrypi.org/raspios_armhf/images/"
            release_index_file = "rpi_os_desk_rel.txt"

        # checking if system argument was specified as the one for the RPI OS Desktop Plus
        elif sys.argv[1] == "rpi_os_desk_plus" and len(sys.argv) == 2: 
            
            # forming an appropriate start URL and an appropriate release index file
            given_link = "https://downloads.raspberrypi.org/raspios_full_armhf/images/"
            release_index_file = "rpi_os_desk_plus_rel.txt"

        # in case if system arguments were specified wrongly
        else:
            print(error_message)
            exit()

        # html page parsing
        soup = html_page_parse(given_link)

        # adding release part to given link
        release_id= search_in_tag("td", "a", "^ra", soup)

        # download url target + last href + smth + /zip
        release_link = given_link + release_id

        # parsing the last release page
        soup_1 = html_page_parse(release_link )

        # finding a .zip release file in the release page
        release_file = search_in_tag("td", "a", ".zip$", soup_1)

        # download link for the last release
        download_link = release_link + release_file

        print (download_link)

        # checking if release index file exists and contains the last release
        if path.exists(release_index_file) and download_link == open(release_index_file).read():
            
            # exiting the script
            exit()
        
        else:

            # creating a new file with a link to the last release
            release_index_file = open(release_index_file, "w")
            release_index_file.write(download_link)
            release_index_file.close

    # in case if system arguments were specified wrongly
    except IndexError:
        
        print(error_message)

if __name__ == "__main__":
    main()