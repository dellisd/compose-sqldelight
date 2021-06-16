// project/webpack.conf.d/fs.js
config.resolve.fallback = {
    fs: false,
    path: false
};

// project/webpack.conf.d/wasm.js
var CopyWebpackPlugin = require('copy-webpack-plugin');
config.plugins.push(
    new CopyWebpackPlugin({
        patterns: [
            {
                from: '../../node_modules/sql.js/dist/sql-wasm.wasm',
                to: '../../../compose-sqldelight/build/distributions'
            }
        ]
    })
);