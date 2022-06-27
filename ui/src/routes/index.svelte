<script lang="ts">

    import type {Account} from "./account";

    let ghostfolioUrl;
    let ghostfolioToken;
    let currencies: Array<{id, curr, accountId}> = [];
    let counter = 0;
    let files;

    let accounts: Array<Account>;

    function fetchAccounts() {
        fetch(ghostfolioUrl + "/api/v1/account", {
            headers: {
                "Authorization": ghostfolioToken,
                "Accept": "application/json"
            }
        })
            .then(response => response.json())
            .then(json => accounts = json.accounts);
    }

    function addCurrency() {
        currencies = [...currencies, {id: counter++, curr: "", accountId: ""}];
    }

    function send() {
        let formData = new FormData();

        formData.append('file', files[0]);
        formData.append('token', ghostfolioToken);
        formData.append('platform', 'Trading212');
        formData.append('accountIdsByCurrency', JSON.stringify(getAccountIdsByCurrency()));

        fetch("http://localhost:8080/upload", {
            body: formData,
            mode: "no-cors",
            headers: {
            },
            method: "post"
        });
    }

    function getAccountIdsByCurrency() {
        let accountIdsByCurrency = {};
        currencies
            .filter(currency => !!currency.curr)
            .forEach(currency => accountIdsByCurrency[currency.curr] = currency.accountId);
        return accountIdsByCurrency;
    }

    const selectAccountId = (currency) => () => {

        const account = accounts.filter(account => account.id === currency.accountId)[0];

        console.log(currency, account);
        if (currency.curr == "") {
            currency.curr = account.currency;
            currencies = currencies;
        }
    }

</script>

<style>
    .container {
        display: grid;
        grid-template-columns: 1fr;
    }
</style>

<div class="container">
    <input id="ghostfolio-url-input" bind:value={ghostfolioUrl} placeholder="Ghostfolio URL"/>
    <input bind:value={ghostfolioToken} placeholder="Ghostfolio Bearer Token"/>
    <button on:click={fetchAccounts}>Fetch accounts</button>

    <input type="file" bind:files placeholder="CSV to upload"/>

    {#if accounts}
        {#each currencies as currency}
            <div>
                <input bind:value={currency.curr} placeholder="e.g USD"/>
                <select bind:value={currency.accountId} on:change={selectAccountId(currency)}>
                    {#each accounts as account}
                        <option value="{account.id}">{account.name}</option>
                    {/each}
                </select>
                <button>X</button>
            </div>
        {/each}


        <button on:click={addCurrency}>Add currency</button>
    {/if}

    <button on:click={send}>Send to Ghostfolio</button>
</div>
