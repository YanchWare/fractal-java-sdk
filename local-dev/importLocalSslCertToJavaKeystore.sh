#!/bin/zsh
set -e

# Define variables
domain="api-local.fractal.cloud"
port=8443
#pathToJavaKeystore="$JAVA_HOME/lib/security/cacerts"
pathToJavaKeystore="/Users/kamil/Library/Java/JavaVirtualMachines/temurin-21.0.3/Contents/Home/lib/security/cacerts"
certFile="$domain.cer"

# Extract the domain and port from the full URL


# Use default HTTPS port if no port is found
if [ -z "$port" ]; then
    port=844
fi

# Export the SSL certificate from the domain and port
echo "Exporting SSL certificate from $domain on port $port"
openssl s_client -connect "${domain}":${port} </dev/null 2>/dev/null | openssl x509 -outform PEM > $certFile

# Verify that the certificate was successfully exported
if [ ! -f "$certFile" ]; then
    echo "Failed to export the SSL certificate."
    exit 1
fi

echo "Certificate exported to $certFile"

# Import the certificate into the Java keystore
echo "Importing certificate into Java keystore"

# First, remove the existing certificate if it exists  
{
    sudo keytool -delete -noprompt \
      -alias "$domain" \
      -keystore "$pathToJavaKeystore"
} || {
    echo "Alias $domain does not exist, proceeding with import"
}


# Then, import the new certificate
sudo keytool -importcert -noprompt \
  -trustcacerts \
  -alias "$domain" \
  -file "$certFile" \
  -keystore "$pathToJavaKeystore"

# Cleanup
echo "Cleaning up..."
rm $certFile
echo "Certificate import process complete."

