import json
from fpdf import FPDF

def convert_json_to_pdf(input_json, output_pdf):
    # Load JSON data
    with open(input_json, 'r') as json_file:
        data = json.load(json_file)
    
    # Create PDF instance
    pdf = FPDF()
    pdf.add_page()

    # Set font
    pdf.set_font("Arial", size=12)
    
    # Iterate through JSON data and add to PDF
    for key, value in data.items():
        pdf.cell(200, 10, txt=f'{key}: {value}', ln=True)

    # Save PDF
    pdf.output(output_pdf)

# Usage example
convert_json_to_pdf('/var/lib/jenkins/workspace/JenkinsPOC/Jenkins-POC-Pipeline/report.json', '/var/lib/jenkins/workspace/JenkinsPOC/Jenkins-POC-Pipeline/report.pdf')
