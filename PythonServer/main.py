import socket
import json

server_host = '127.0.0.1'
server_port = 25567

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind((server_host, server_port))
server_socket.listen(1)

print(f"Server listening on {server_host}:{server_port}")

try:
    client_socket, client_address = server_socket.accept()
    print(f"Connection from {client_address}")

    while True:
        data = client_socket.recv(1024).decode('utf-8')

        json_data = json.loads(data)

        print("Received data:", json_data)
except KeyboardInterrupt:
    print("Server interrupted. Closing the server.")
finally:
    server_socket.close()

