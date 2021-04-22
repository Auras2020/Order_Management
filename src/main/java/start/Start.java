package start;

import presentation.*;

public class Start {

        public static void main(String[] args) {

            ViewClients viewClients =new ViewClients();
            ControllerClients controllerClients =new ControllerClients(viewClients);

            ViewProducts viewProducts=new ViewProducts();
            ControllerProducts controllerProducts=new ControllerProducts(viewProducts);

            ViewOrders viewOrders=new ViewOrders();
            ControllerOrders controllerOrders=new ControllerOrders(viewOrders, viewProducts);
        }

}


