package tacos.data;

import tacos.domain.Order;

public interface OrderRepository {
	public Order save(Order order);
}
