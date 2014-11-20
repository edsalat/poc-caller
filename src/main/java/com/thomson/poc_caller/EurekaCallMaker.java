package com.thomson.poc_caller;

import com.google.inject.AbstractModule;
import com.google.inject.binder.LinkedBindingBuilder;
import com.netflix.appinfo.AmazonInfo;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.Builder;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.CloudInstanceConfigProvider;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.EurekaNamespace;
import com.netflix.discovery.providers.DefaultEurekaClientConfigProvider;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.netflix.loadbalancer.ServerListFilter;
import com.netflix.loadbalancer.ZoneAffinityServerListFilter;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

public class EurekaCallMaker extends AbstractModule{
	static Server expected = new Server("www.example.com", 8001);
    
	private DiscoveryEnabledServer createServer(String appName, String host, int port, String zone) {
        AmazonInfo amazonInfo = AmazonInfo.Builder.newBuilder().addMetadata(AmazonInfo.MetaDataKey.availabilityZone, zone).build();
        
        Builder builder = InstanceInfo.Builder.newBuilder();
        InstanceInfo info = builder.setAppName(appName)
        .setDataCenterInfo(amazonInfo)
        .setHostName(host)
        .setPort(port)
        .build();
        DiscoveryEnabledServer server = new DiscoveryEnabledServer(info, false, false);
        server.setZone(zone);
        return server;
    }
    
	
	@Override
    protected void configure() {
//        bind(com.netflix.appinfo.HealthCheckHandler.class).to(EurekaHealthCheckHandler.class);
//        bind(ApplicationInfoManager.class).asEagerSingleton();
//        bind(DiscoveryClient.class).asEagerSingleton();

        configureEureka();
    }

	protected void configureEureka() {
        bindEurekaNamespace().toInstance("eureka.");
        bindEurekaInstanceConfig().toProvider(CloudInstanceConfigProvider.class);
        bindEurekaClientConfig().toProvider(DefaultEurekaClientConfigProvider.class);
    }
	
	 protected LinkedBindingBuilder<EurekaInstanceConfig> bindEurekaInstanceConfig() {
	        return bind(EurekaInstanceConfig.class);
	    }

	    protected LinkedBindingBuilder<EurekaClientConfig > bindEurekaClientConfig() {
	        return bind(EurekaClientConfig.class);
	    }

	    protected LinkedBindingBuilder<String> bindEurekaNamespace() {
	        return bind(String.class).annotatedWith(EurekaNamespace.class);
	    }

	public void eurekaCallTest() throws Exception{
//		Karyon.forSuites(new ArchaiusSuite("eureka-client"));
		
		
	//	configure();
    
//		 Karyon.forRequestHandler(7001,
//                 new SimpleRouter(), /* Use this instead of RouterWithInterceptors below if interceptors are not required */
//                 //new RouterWithInterceptors(),
//                 new ArchaiusSuite("eureka-client"),
//                 SampleModule.asSuite(),
//                 KaryonEurekaModule.asSuite(), /* Uncomment if you need eureka */
//                 /*KaryonWebAdminModule.asSuite(),*/
//                 ShutdownModule.asSuite())/*,
//                 KaryonServoModule.asSuite())*/
//                 .startAndWaitTillShutdown();

		
		System.setProperty("eureka.region", "default");
        System.setProperty("eureka.environment", "test");
        System.setProperty("eureka.client.props", "eureka-client");
		ConfigurationManager.getConfigInstance().setProperty("DiscoveryEnabledNIWSServerList.failFastOnNullVip", false);
		
        DiscoveryManager.getInstance().initComponent(
                new MyDataCenterInstanceConfig(),
                new DefaultEurekaClientConfig());


		
	//	DiscoveryClient discoveryClient = DiscoveryManager.getInstance().getDiscoveryClient();
//		InstanceInfo nextServerInfo = discoveryClient.getNextServerFromEureka("karyon-gradle.mydomain.net", false);//"eureka.mydomain.net", false);
//
//        Socket s = new Socket();
//        int serverPort = nextServerInfo.getPort();
//        try {
//            s.connect(new InetSocketAddress(nextServerInfo.getHostName(),
//                    serverPort));
//        } catch (IOException e) {
//            System.err.println("Could not connect to the server :"
//                    + nextServerInfo.getHostName() + " at port " + serverPort);
//        }

		
		
//		The server list class that fetches the server information from Eureka client. ServerList is used by
//		DiscoveryEnabledNIWSServerList deList = new DiscoveryEnabledNIWSServerList("localhost:8080/v2/");
		
	
		
//		ConfigurationManager.getConfigInstance().setProperty("DiscoveryEnabled.testDefaultHonorsVipPortDefinition.ribbon.DeploymentContextBasedVipAddresses", "dummy");
//        ConfigurationManager.getConfigInstance().setProperty("DiscoveryEnabled.testDefaultHonorsVipPortDefinition.ribbon.IsSecure", "false");
//        ConfigurationManager.getConfigInstance().setProperty("DiscoveryEnabled.testDefaultHonorsVipPortDefinition.ribbon.Port", "6999");
//        ConfigurationManager.getConfigInstance().setProperty("DiscoveryEnabled.testDefaultHonorsVipPortDefinition.ribbon.TargetRegion", "region");
//        ConfigurationManager.getConfigInstance().setProperty("DiscoveryEnabled.testDefaultHonorsVipPortDefinition.ribbon.NIWSServerListClassName", DiscoveryEnabledNIWSServerList.class.getName());
//
//
//
//        DiscoveryEnabledNIWSServerList deList = new DiscoveryEnabledNIWSServerList();
//
//		DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.class.newInstance();
//        clientConfig.loadProperties("DiscoveryEnabled.testDefaultHonorsVipPortDefinition");
//        deList.initWithNiwsConfig(clientConfig);

//		List<DiscoveryEnabledServer> servers = new ArrayList<DiscoveryEnabledServer>();        
		//http://localhost:8081/v2/\
		//us-east-1
//		servers.add(createServer( "karyon-gradle" /*"mvp-edge"*/, "localhost:8081/v2", 8081, "Us-east-1" ));
		
        
		
		IRule rule = new AvailabilityFilteringRule();
		DefaultClientConfigImpl clConfig = DefaultClientConfigImpl.getClientConfigWithDefaultValues("poc-caller");
		ServerList<DiscoveryEnabledServer> list = new DiscoveryEnabledNIWSServerList("poc-middletier");  //should ipClientConfig
        
        ServerListFilter<DiscoveryEnabledServer> filter = new ZoneAffinityServerListFilter<DiscoveryEnabledServer>();
        ZoneAwareLoadBalancer<DiscoveryEnabledServer> lb = LoadBalancerBuilder.<DiscoveryEnabledServer>newBuilder()
                .withDynamicServerList(list)
                .withRule(rule)
              //  .withServerListFilter(filter)
                .buildDynamicServerListLoadBalancer();
        //assertNotNull(lb);
        //assertEquals(Lists.newArrayList(expected), lb.getServerList(false));
        //assertSame(filter, lb.getFilter());
        //assertSame(list, lb.getServerListImpl());
        for(int i= 0 ; i< 4; i++){
        	Server server1 = lb.chooseServer();
        	System.out.println(server1.getHost() + server1.getPort() );
        	Server server2 = lb.chooseServer();
        	System.out.println(server2.getHost() + server2.getPort() );
        }
        // make sure load balancer does not recreate the server instance
        //assertTrue(server instanceof DiscoveryEnabledServer);
        //System.out.println(lb);
	}

	public static void main(String[] args){
		EurekaCallMaker caller = new EurekaCallMaker();
		try {
			caller.eurekaCallTest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

