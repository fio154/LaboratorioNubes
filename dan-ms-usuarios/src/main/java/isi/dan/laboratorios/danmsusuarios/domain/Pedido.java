package isi.dan.laboratorios.danmsusuarios.domain;

public class Pedido {
    
    private Integer id;
	private Instant fechaPedido;
    private List<DetallePedido> detallesPedido;
    private EstadoPedido estado;
    private Obra obra;
	
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Instant getFechaPedido() {
		return fechaPedido;
	}
	public void setFechaPedido(Instant fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
    public List<DetallePedido> getDetallesPedido() {
		return detallesPedido;
	}
	public void setDetallesPedido(List<DetallePedido> detallesPedido) {
		this.detallesPedido = detallesPedido;
	}
	public EstadoPedido getEstado() {
		return estado;
	}
	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}
    public Obra getObra() {
		return obra;
	}
	public void setObra(Obra obra) {
		this.obra = obra;
	}
}
