# Balanceador de carga (Load Balancer)

O prefixo `lb://` é usado em rotas de Spring Cloud para indicar que a solicitação deve ser roteada através de um balanceador de carga (Load Balancer) configurado, geralmente fornecido por serviços como o Ribbon ou Spring Cloud LoadBalancer. 
Explicação mais detalhada:

1. **Load Balancer (Balanceador de Carga)**: Um balanceador de carga distribui o tráfego de entrada entre várias instâncias de um serviço para garantir a alta disponibilidade e a escalabilidade do sistema. Ele pode distribuir o tráfego de maneira uniforme ou baseada em diferentes algoritmos de balanceamento de carga.

2. **Ribbon**: O Ribbon é uma biblioteca cliente da Netflix que oferece balanceamento de carga entre instâncias de serviços. Ele é frequentemente usado em conjunto com o Eureka para descoberta de serviços e permite que os clientes se comuniquem com várias instâncias de um serviço de forma transparente.

3. **Prefixo `lb://`**: Ao usar o prefixo `lb://` em rotas de Spring Cloud, como em `lb://service-name`, você está instruindo o sistema a rotear a solicitação para o serviço chamado `service-name` por meio do balanceador de carga configurado. Isso permite que o Ribbon (ou outro balanceador de carga configurado) selecione dinamicamente uma instância disponível do serviço `service-name` para atender à solicitação.

4. **Benefícios**:
    - **Balanceamento de carga**: O prefixo `lb://` oferece balanceamento de carga automático entre várias instâncias de um serviço, ajudando a distribuir o tráfego de forma equilibrada e melhorar a disponibilidade.
    - **Descoberta de Serviços**: Ao combinar o prefixo `lb://` com serviços de descoberta de serviço como o Eureka, as instâncias de serviço podem ser dinamicamente adicionadas ou removidas do pool de servidores sem afetar o cliente.

Em resumo, o prefixo `lb://` em rotas de Spring Cloud é usado para indicar que uma solicitação deve ser roteada através de um balanceador de carga configurado, permitindo o balanceamento de carga automático entre instâncias de um serviço. Isso contribui para uma arquitetura de microservices mais resiliente e escalável.

# Anotação @LoadBalanced

A anotação `@LoadBalanced` é uma anotação do Spring Framework, comumente usada em aplicativos que fazem uso de serviços de descoberta e balanceamento de carga, como o Netflix Ribbon, em conjunto com o Spring Cloud. Essa anotação é usada em beans do tipo RestTemplate ou WebClient para indicar que esses objetos devem ser configurados para realizar chamadas a serviços usando um balanceador de carga configurado.

Como funciona e por que é útil:

1. **Balanceamento de carga**: O objetivo principal do `@LoadBalanced` é permitir que os clientes façam chamadas a serviços que estão registrados em um servidor de descoberta (como o Eureka) e se beneficiem do balanceamento de carga entre as múltiplas instâncias desse serviço.

2. **Descoberta de serviço**: Em ambientes de microservices, é comum ter várias instâncias de um mesmo serviço em execução para garantir a escalabilidade e a disponibilidade. O `@LoadBalanced` permite que os clientes encontrem e acessem essas instâncias sem precisar conhecer seus endereços individuais, já que o balanceador de carga é responsável por rotear as requisições de forma transparente.

3. **Funcionamento com RestTemplate e WebClient**: Quando anota um bean RestTemplate ou WebClient com `@LoadBalanced`, o Spring Cloud cria um proxy ao redor desses objetos. Esse proxy intercepta as chamadas aos métodos `RestTemplate#restTemplate.exchange()` ou `WebClient#webClient.get()`, por exemplo, e substitui o hostname do URL com o nome do serviço registrado no servidor de descoberta. Isso permite que as chamadas sejam roteadas através do balanceador de carga para uma das instâncias disponíveis do serviço.

4. **Configuração simplificada**: O uso do `@LoadBalanced` simplifica a configuração de clientes de serviços em um ambiente de microservices, pois elimina a necessidade de configurar manualmente o hostname e a porta do serviço de destino. Em vez disso, basta usar o nome lógico do serviço e deixar o balanceador de carga cuidar da resolução do endereço para uma instância específica.

Em resumo, a anotação `@LoadBalanced` é uma ferramenta poderosa para facilitar o acesso a serviços em um ambiente de microservices, permitindo que os clientes se beneficiem do balanceamento de carga e da descoberta de serviços de forma transparente e sem a necessidade de configuração manual.

---

# Autor
## Feito por: `Daniel Penelva de Andrade`