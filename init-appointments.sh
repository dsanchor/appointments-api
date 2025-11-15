#!/bin/bash

# Initialize appointments for multiple customer IDs
# Usage: ./init-appointments.sh [API_URL]

API_URL="${1:-http://localhost:8080}"
ENDPOINT="${API_URL}/api/appointments"

# Customer IDs to create appointments for
CUSTOMER_IDS=("123456789A" "123456789B" "123456789C" "123456789D")

echo "Initializing appointments for customers..."
echo "API URL: ${ENDPOINT}"
echo ""

# Function to create an appointment
create_appointment() {
    local customer_id=$1
    local title=$2
    local category=$3
    local notes=$4
    local start_date=$5
    
    local payload=$(cat <<EOF
{
  "title": "${title}",
  "notes": "${notes}",
  "category": "${category}",
  "startDate": "${start_date}",
  "done": false,
  "customerId": "${customer_id}"
}
EOF
)
    
    echo "Creating appointment for customer ${customer_id}: ${title}"
    response=$(curl -s -w "\n%{http_code}" -X POST "${ENDPOINT}" \
        -H "Content-Type: application/json" \
        -d "${payload}")
    
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | sed '$d')
    
    if [ "$http_code" -eq 201 ] || [ "$http_code" -eq 200 ]; then
        echo "✓ Successfully created appointment (HTTP ${http_code})"
        echo "  Response: ${body}"
    else
        echo "✗ Failed to create appointment (HTTP ${http_code})"
        echo "  Response: ${body}"
    fi
    echo ""
}

# Get current date and future dates for appointments
current_date=$(date -u +"%Y-%m-%dT%H:%M:%S")
tomorrow=$(date -u -d "+1 day" +"%Y-%m-%dT%H:%M:%S" 2>/dev/null || date -u -v+1d +"%Y-%m-%dT%H:%M:%S")
next_week=$(date -u -d "+7 days" +"%Y-%m-%dT%H:%M:%S" 2>/dev/null || date -u -v+7d +"%Y-%m-%dT%H:%M:%S")
next_month=$(date -u -d "+30 days" +"%Y-%m-%dT%H:%M:%S" 2>/dev/null || date -u -v+30d +"%Y-%m-%dT%H:%M:%S")

# Create appointments for customer 123456789A
create_appointment "123456789A" "Annual Health Checkup" "Medical" "Regular annual physical examination" "${tomorrow}"
create_appointment "123456789A" "Follow-up Consultation" "Medical" "Follow-up from previous visit" "${next_week}"

# Create appointments for customer 123456789B
create_appointment "123456789B" "Dental Cleaning" "Dental" "Routine dental cleaning and checkup" "${tomorrow}"
create_appointment "123456789B" "Financial Planning Session" "Finance" "Quarterly financial review" "${next_month}"

# Create appointments for customer 123456789C
create_appointment "123456789C" "Car Service Appointment" "Automotive" "Regular vehicle maintenance" "${next_week}"
create_appointment "123456789C" "Home Inspection" "Property" "Annual home inspection" "${next_month}"

# Create appointments for customer 123456789D
create_appointment "123456789D" "Legal Consultation" "Legal" "Contract review meeting" "${tomorrow}"
create_appointment "123456789D" "Tax Preparation Meeting" "Finance" "Annual tax preparation" "${next_month}"

echo "===================="
echo "Initialization complete!"
echo ""
echo "To view all appointments:"
echo "curl ${API_URL}/api/appointments"
