import requests

url = 'http://localhost:8080/Lab1_war_exploded/lab1Servlet'
input = {'key': 'cherry',
         'value': 10,
         'mock': 'False',
         'sync': 'False'}

response_html = requests.post(url, data = input)

response_text = response_html.text.replace("<br>", "\n").replace("<html>", "").replace("</html>", "")\
                                                        .replace("<h2>", "").replace("</h2>", "")

print(response_text)
