from bs4 import BeautifulSoup
import urllib.request

# target website
website = "website.com"

# html page parsing
html_page = urllib.request.urlopen(website)
soup = BeautifulSoup(html_page, "html.parser")

# all hrefs_list
all_href = []

# iterating through all hrefs
for link in soup.findAll('a'):
    print(link.get('href'))
    all_href.append(link.get('href'))

# download url target + last href + smth + /zip
url = 'url target + last href + smth + /zip'
urllib.request.urlretrieve(url, 'stored path')