from bs4 import BeautifulSoup
import urllib.request

def html_page_parse(given_page):
    html_page = urllib.request.urlopen(given_page)
    soup = BeautifulSoup(html_page, "html.parser")
    return soup

# target website
given_link = "https://downloads.raspberrypi.org/raspios_lite_armhf/images/"

# html page parsing
soup = html_page_parse(given_link)

# all hrefs_list
all_href = []

# iterating through all hrefs
for link in soup.findAll('a'):
    #print(link.get('href'))
    all_href.append(link.get('href'))

# download url target + last href + smth + /zip
link_modified = given_link + all_href[-1]
#print (link_modified)

release_id = ''
soup = html_page_parse(link_modified)
for td in soup.find_all(lambda tag: tag.name=='td' and tag.text.strip().endswith('.zip')):
    link = td.find_next('a')
    #print(td.get_text(strip=True), link['href'] if link else '')
    release_id = (link['href'] if link else '')

#print(release_id)

download_link = link_modified + release_id
print (download_link)