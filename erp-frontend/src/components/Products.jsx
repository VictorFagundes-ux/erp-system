import { useState, useEffect } from 'react';
import api from '../services/api';

export default function Products() {
    const [products, setProducts] = useState([]);
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [sku, setSku] = useState('');
    const [price, setPrice] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    // Função para buscar a lista de produtos do backend
    const fetchProducts = async () => {
        try {
            const response = await api.get('/products');
            setProducts(response.data);
        } catch (err) {
            console.error('Erro ao buscar produtos:', err);
            setError('Não foi possível carregar a lista de produtos.');
        }
    };

    // Carrega os produtos assim que o componente abre
    useEffect(() => {
        fetchProducts();
    }, []);

    // Função para cadastrar um novo produto
    const handleCreateProduct = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');

        try {
            await api.post('/products', {
                name,
                description,
                sku,
                price: parseFloat(price),
            });

            setSuccess('Produto cadastrado com sucesso!');
            setName('');
            setDescription('');
            setSku('');
            setPrice('');

            // Atualiza a lista de produtos
            fetchProducts();
        } catch (err) {
            console.error('Erro ao cadastrar produto:', err);
            setError('Erro ao salvar o produto. Verifique os dados.');
        }
    };

    return (
        <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto', fontFamily: 'sans-serif' }}>
            <h2>Gerenciamento de Produtos</h2>

            {error && <div style={{ color: 'red', marginBottom: '10px' }}>{error}</div>}
            {success && <div style={{ color: 'green', marginBottom: '10px' }}>{success}</div>}

            {/* Formulário de Cadastro */}
            <form onSubmit={handleCreateProduct} style={{ background: '#f9f9f9', padding: '15px', borderRadius: '8px', marginBottom: '20px', border: '1px solid #ddd' }}>
                <h3>Novo Produto</h3>
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px', marginBottom: '10px' }}>
                    <input
                        type="text"
                        placeholder="Nome do Produto"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                        style={{ padding: '8px' }}
                    />
                    <input
                        type="text"
                        placeholder="SKU (Ex: TEC-001)"
                        value={sku}
                        onChange={(e) => setSku(e.target.value)}
                        required
                        style={{ padding: '8px' }}
                    />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <input
                        type="text"
                        placeholder="Descrição"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        style={{ width: '100%', padding: '8px', boxSizing: 'border-box' }}
                    />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <input
                        type="number"
                        step="0.01"
                        placeholder="Preço (R$)"
                        value={price}
                        onChange={(e) => setPrice(e.target.value)}
                        required
                        style={{ width: '100%', padding: '8px', boxSizing: 'border-box' }}
                    />
                </div>
                <button type="submit" style={{ padding: '10px 15px', backgroundColor: '#28a745', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
                    Cadastrar Produto
                </button>
            </form>

            {/* Tabela de Listagem */}
            <h3>Produtos Cadastrados</h3>
            <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '10px' }}>
                <thead>
                <tr style={{ background: '#007bff', color: '#fff', textAlign: 'left' }}>
                    <th style={{ padding: '10px', border: '1px solid #ddd' }}>ID</th>
                    <th style={{ padding: '10px', border: '1px solid #ddd' }}>Nome</th>
                    <th style={{ padding: '10px', border: '1px solid #ddd' }}>SKU</th>
                    <th style={{ padding: '10px', border: '1px solid #ddd' }}>Preço</th>
                </tr>
                </thead>
                <tbody>
                {products.length === 0 ? (
                    <tr>
                        <td colSpan="4" style={{ padding: '15px', textAlign: 'center', border: '1px solid #ddd' }}>
                            Nenhum produto cadastrado ainda.
                        </td>
                    </tr>
                ) : (
                    products.map((p) => (
                        <tr key={p.id}>
                            <td style={{ padding: '10px', border: '1px solid #ddd' }}>{p.id}</td>
                            <td style={{ padding: '10px', border: '1px solid #ddd' }}>{p.name}</td>
                            <td style={{ padding: '10px', border: '1px solid #ddd' }}>{p.sku}</td>
                            <td style={{ padding: '10px', border: '1px solid #ddd' }}>R$ {Number(p.price).toFixed(2)}</td>
                        </tr>
                    ))
                )}
                </tbody>
            </table>
        </div>
    );
}